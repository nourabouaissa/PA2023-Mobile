package com.example.myapplication.ui.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.FriendRequest
import com.example.myapplication.newtwork.response.AddFriendResponse
import com.example.myapplication.ui.dashboard.GamesData
import com.example.myapplication.ui.my_account.FriendsRequestViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendsRequestAdapter(private val friendsList: List<FriendRequest>,val viewModel: FriendsRequestViewModel) : RecyclerView.Adapter<FriendsRequestAdapter.FriendRequestViewHolder>() {

    // Crée le ViewHolder pour chaque élément de la liste
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friends_request_item, parent, false)
        return FriendRequestViewHolder(view)
    }

    // Lie les données du modèle à la vue de l'élément
    override fun onBindViewHolder(holder: FriendRequestViewHolder, position: Int) {
        val friend = friendsList[position]
        holder.bind(friend,viewModel)
    }

    // Retourne le nombre total d'éléments dans la liste
    override fun getItemCount(): Int {
        return friendsList.size
    }

    // Classe ViewHolder qui maintient les références aux éléments de la vue pour chaque élément de la liste
    class FriendRequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView: TextView = itemView.findViewById(R.id.friend_username_tv)
        private val friendRequestButton:Button=itemView.findViewById(R.id.friends_request_btn)
        // Lie les données du modèle à la vue de l'élément
        fun bind(friend: FriendRequest,viewModel:FriendsRequestViewModel) {
           nameTextView.text=friend.username
            friendRequestButton.setOnClickListener {
                 val call=viewModel.acceptFriend(friend.asc_id)
                call.enqueue(object : Callback<AddFriendResponse> {
                    override fun onResponse(call: Call<AddFriendResponse>, response: Response<AddFriendResponse>) {
                        if (response.isSuccessful) {
                            val addFriendResponse = response.body()
                            if(addFriendResponse!=null){
                                Toast.makeText(itemView.context,"Friend request accepted successfully",Toast.LENGTH_SHORT).show()
                                viewModel.friendRequest()
                            }

                        } else {
                            Toast.makeText(
                                itemView.context,
                                response.errorBody()?.string(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<AddFriendResponse>, t: Throwable) {
                        Toast.makeText(
                            itemView.context,
                            t.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }

        }
    }
}