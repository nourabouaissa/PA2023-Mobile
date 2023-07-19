package com.example.myapplication.ui.home

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
import com.example.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textHome
        //homeViewModel.text.observe(viewLifecycleOwner) {
        //    textView.text = it
        //}
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        homeViewModel.gamesList.observe(viewLifecycleOwner, Observer { list ->
            val friendsRecyclerView: RecyclerView = view.findViewById(R.id.games_recyclerview)
            val friendsAdapter = GamesAdapter(list)
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
class GamesAdapter(private val friendsList: List<Game>) : RecyclerView.Adapter<GamesAdapter.FriendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return FriendViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val currentFriend = friendsList[position]
        holder.nameTextView.text = currentFriend.username
        // Vous pouvez également utiliser une bibliothèque comme Glide pour charger l'image depuis l'URL.
        // holder.photoImageView.setImageFromUrl(currentFriend.photoUrl)
    }

    override fun getItemCount(): Int {
        return friendsList.size
    }

    inner class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.game_title)
        // val photoImageView: ImageView = itemView.findViewById(R.id.game_image) - si vous avez une image dans votre layout
    }
}