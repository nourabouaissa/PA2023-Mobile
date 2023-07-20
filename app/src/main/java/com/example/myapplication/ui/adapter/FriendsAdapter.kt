package com.example.myapplication.ui.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class FriendsAdapter(private val friendsList: List<FriendsAdapter>) : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    // Crée le ViewHolder pour chaque élément de la liste
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)
        return FriendViewHolder(view)
    }

    // Lie les données du modèle à la vue de l'élément
    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friendsList[position]
        holder.bind(friend)
    }

    // Retourne le nombre total d'éléments dans la liste
    override fun getItemCount(): Int {
        return friendsList.size
    }

    // Classe ViewHolder qui maintient les références aux éléments de la vue pour chaque élément de la liste
    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)

        // Lie les données du modèle à la vue de l'élément
        fun bind(friend: FriendsAdapter) {
          //  nameTextView.text = friend.name

        }
    }
}