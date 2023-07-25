package com.example.myapplication.ui.my_account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentFriendsRequestBinding
import com.example.myapplication.ui.adapter.FriendsRequestAdapter
import com.example.myapplication.ui.dashboard.GamesAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FriendsRequestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FriendsRequestFragment : Fragment() {

    private var _binding: FragmentFriendsRequestBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsRequestBinding.inflate(inflater, container, false)
        val rootView: View = binding.root


        return rootView
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val friendsRequestViewModel =
            ViewModelProvider(this).get(FriendsRequestViewModel::class.java)

        friendsRequestViewModel.friendRequest.observe(viewLifecycleOwner, Observer { friendsRequest ->
            val friendRequestRecyclerview: RecyclerView =binding.friendsRequestRecyclerview
            val friendsAdapter = FriendsRequestAdapter(friendsRequest,friendsRequestViewModel)
            friendRequestRecyclerview.adapter = friendsAdapter
            friendRequestRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        })
    }

}