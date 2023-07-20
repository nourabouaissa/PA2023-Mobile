package com.example.myapplication.ui

import ApiClient
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.AddFriendFragmentBinding
import com.example.myapplication.ui.my_account.Ami

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddFriendFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFriendFragment : DialogFragment() {

    private var _binding: AddFriendFragmentBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy{
        findNavController()
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
        binding.ajouterButton.setOnClickListener{

            val friendName = binding.AddFriend.text.toString().trim()

            // Check if the entered name is not empty
            if (friendName.isNotEmpty()) {
                // Add the friend to the list
                ApiClient.addFriend(Ami(friendName))
//                newFriend
//
//                // Notify the adapter that the data set has changed
//                (binding.friendsRecyclerView.adapter as? FriendsAdapter)?.notifyDataSetChanged()
                navController.navigateUp()

                // Close the dialog

            } else {
                // Display an error message or toast if the name is empty
                // For example:
                Toast.makeText(requireContext(), "Enter a valid friend's name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
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