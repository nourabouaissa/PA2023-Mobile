package com.example.myapplication.ui.my_account

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.LoginActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAccountBinding
import com.example.myapplication.ui.AddFriendFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso


class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val navController by lazy{
        findNavController()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val accountViewModel =
            ViewModelProvider(this).get(AccountViewModel::class.java)

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        accountViewModel.userInfo.observe(viewLifecycleOwner) {
          /*  Glide.with(this)
                .load(it.url_image)
              *//*  .placeholder(R.drawable.default_avatar) // Image par défaut en cas de chargement
                .error(R.drawable.default_avatar) // Image par défaut en cas d'erreur de chargement*//*
                .into(binding.avatarImageView)*/
            Picasso.get().load(it.url_image).into(binding.avatarImageView)
            binding.emailTextView.text = it.email

            val friendsRecyclerView: RecyclerView = binding.friendsRecyclerView
            val friendsAdapter = FriendsAdapter(it.friends)
            friendsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            friendsRecyclerView.adapter = friendsAdapter

        }



        val fab: FloatingActionButton = root.findViewById(R.id.floating_button)
        fab.setOnClickListener {
            showCustomDialog()
        }
        val logoutButton: Button = binding.logButton
        logoutButton.setOnClickListener {
            // Rediriger vers la page de connexion
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish() // Optionnel, pour fermer l'activité actuelle après la redirection
        }
        return root
    }
    private fun showCustomDialog() {
//        val dialog = Dialog(requireContext(), R.style.CustomDialogTheme)
//
//        val closeButton = dialog.findViewById<Button>(R.id.cancel_button)
        navController.navigate(R.id.action_navigation_notifications_to_add_friend_fragment)
        // Create and show the dialog.

        // Create and show the dialog.

//        closeButton.setOnClickListener {
//            dialog.dismiss() // Fermer la fenêtre modale lorsque le bouton est cliqué
//        }
       /* val AddButton = dialog.findViewById<Button>(R.id.AddFriend)
        AddButton.setOnClickListener {

            val friendName = AddButton.text.toString().trim()

            // Check if the entered name is not empty
            if (friendName.isNotEmpty()) {
                // Add the friend to the list
                val newFriend = FriendNew(friendName)
                newFriend.username

                // Notify the adapter that the data set has changed
                (binding.friendsRecyclerView.adapter as? FriendsAdapter)?.notifyDataSetChanged()

                // Close the dialog

            } else {
                // Display an error message or toast if the name is empty
                // For example:
                Toast.makeText(requireContext(), "Enter a valid friend's name", Toast.LENGTH_SHORT).show()
            }
        }*/

//        dialog.show()
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
        holder.nameTextView.text = currentFriend.username

    }

    override fun getItemCount(): Int {
        return friendsList.size
    }

    inner class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.friend_title)
        // val photoImageView: ImageView = itemView.findViewById(R.id.game_image) - si vous avez une image dans votre layout
    }
}