package com.example.myapplication.ui.dashboard


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDashboardBinding
import com.example.myapplication.databinding.FragmentFriendsRequestBinding
import com.example.myapplication.model.PartyInfo
import com.example.myapplication.ui.my_account.FriendsRequestViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val rootView: View = binding.root


        return rootView
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        dashboardViewModel.friendsList.observe(viewLifecycleOwner, Observer { gamesList ->
            val friendsRecyclerView: RecyclerView = view.findViewById(R.id.friends_recyclerview)
            val friendsAdapter = GamesAdapter(gamesList?: emptyList())
            friendsRecyclerView.adapter = friendsAdapter
            friendsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

//Adapter
class GamesAdapter(private val friendsList: List<PartyInfo>) : RecyclerView.Adapter<GamesAdapter.FriendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return FriendViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val currentGame = friendsList[position]
        holder.nameTextView.text = currentGame.title

    }

    override fun getItemCount(): Int {
        return friendsList.size
    }

    inner class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.game_title)
    }
}