package com.example.myapplication.ui.my_account

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.AddFriendFragmentBinding
import com.example.myapplication.model.UserInfo
import com.example.myapplication.newtwork.response.AddFriendResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [AddFriendFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFriendFragment : DialogFragment() {

    private var _binding: AddFriendFragmentBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy {
        findNavController()
    }

    private val PREF_ACCESS_TOKEN = "access_token"
    private val PREF_ID = "user_id"

    private val sharedPreferences  by lazy {
        this.activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = AddFriendFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancelButton.setOnClickListener {
            navController.navigateUp()
        }
        binding.ajouterButton.setOnClickListener {

            val friendName = binding.AddFriend.text.toString().trim()
            val accessToken = sharedPreferences?.getString(PREF_ACCESS_TOKEN, null)
            val userId = sharedPreferences?.getString(PREF_ID, null)
            // Check if the enter   ed name is not empty
            if (friendName.isNotEmpty() && accessToken != null && userId != null) {
                // Add the friend to the list
                var addFriendResponse: AddFriendResponse?=null
                ApiClient.getUserByUsername(accessToken, friendName)
                    .enqueue(object : Callback<UserInfo> {
                        override fun onResponse(
                            call: Call<UserInfo>,
                            response: Response<UserInfo>
                        ) {
                            if (response.isSuccessful) {
                                val userInfo = response.body()!!
                                ApiClient.addFriend(userId, userInfo.id.toString())
                                    .enqueue(object : Callback<AddFriendResponse> {
                                        override fun onResponse(
                                            call: Call<AddFriendResponse>,
                                            response: Response<AddFriendResponse>
                                        ) {
                                            if (response.isSuccessful) {
                                                addFriendResponse = response.body()!!
                                                Toast.makeText(
                                                    this@AddFriendFragment.requireContext(),
                                                    "Demande d\'amis envoyée pour $friendName",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                // Friend added successfully, handle the response if needed
                                                navController.navigateUp()
                                            } else {
                                                Toast.makeText(
                                                    this@AddFriendFragment.requireContext(),
                                                    response.errorBody()?.string(),
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                // Handle error response
                                                Log.e(
                                                    "AddFriend",
                                                    "Failed to add friend: ${response.code()}"
                                                )
                                            }
                                        }

                                        override fun onFailure(
                                            call: Call<AddFriendResponse>,
                                            t: Throwable
                                        ) {
                                            Toast.makeText(
                                                this@AddFriendFragment.requireContext(),
                                                t.message,
                                                Toast.LENGTH_LONG
                                            ).show()
                                            // Handle network failure
                                            Log.e(
                                                "AddFriend",
                                                "Erreur lors de l'appel API: ${t.message}"
                                            )
                                        }
                                    })
                                Log.d("getUserByUsername", "Friend added successfully")
                            } else {
                                // Handle error response
                                Toast.makeText(
                                    this@AddFriendFragment.requireContext(),
                                    "user not found",
                                    Toast.LENGTH_LONG
                                ).show()
                                Log.e(
                                    "getUserByUsername",
                                    "Failed to add friend: ${response.code()}"
                                )
                            }
                        }

                        override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                            // Handle network failure
                            Toast.makeText(
                                this@AddFriendFragment.requireContext(),
                                "$t.message",
                                Toast.LENGTH_LONG
                            ).show()

                            Log.e("getUserByUsername", "Erreur lors de l'appel API: ${t.message}")
                        }
                    })

//                newFriend
//
//                // Notify the adapter that the data set has changed
//                (binding.friendsRecyclerView.adapter as? FriendsAdapter)?.notifyDataSetChanged()


                // Close the dialog

            } else {
                // Display an error message or toast if the name is empty
                // For example:
                Toast.makeText(requireContext(), "Enter a valid friend's name", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddFriendFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            AddFriendFragment()
    }
}