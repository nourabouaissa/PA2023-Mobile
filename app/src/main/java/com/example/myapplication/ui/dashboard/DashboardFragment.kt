package com.example.myapplication.ui.dashboard

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDashboardBinding
import com.example.myapplication.ui.home.Game
import com.example.myapplication.ui.home.GamesAdapter
import com.example.myapplication.ui.home.HomeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val rootView: View = binding.root

        val fab: FloatingActionButton = rootView.findViewById(R.id.floating_button)
        fab.setOnClickListener {
            showCustomDialog()
        }

        return rootView
    }

    private fun showCustomDialog() {
        val dialog = Dialog(requireContext(), R.style.CustomDialogTheme)
        dialog.setContentView(R.layout.add_friend_fragment)

        val closeButton = dialog.findViewById<Button>(R.id.close_button)
        closeButton.setOnClickListener {
            dialog.dismiss() // Fermer la fenêtre modale lorsque le bouton est cliqué
        }

        dialog.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        dashboardViewModel.friendsList.observe(viewLifecycleOwner, Observer { friendsList ->
            val friendsRecyclerView: RecyclerView = view.findViewById(R.id.friends_recyclerview)
            val friendsAdapter = FriendsAdapter(friendsList)
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
class FriendsAdapter(private val friendsList: List<Friend>) : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)
        return FriendViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val currentFriend = friendsList[position]
        holder.nameTextView.text = currentFriend.first_name
        // Vous pouvez également utiliser une bibliothèque comme Glide pour charger l'image depuis l'URL.
        // holder.photoImageView.setImageFromUrl(currentFriend.photoUrl)
    }

    override fun getItemCount(): Int {
        return friendsList.size
    }

    inner class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.friend_title)
        // val photoImageView: ImageView = itemView.findViewById(R.id.game_image) - si vous avez une image dans votre layout
    }
}