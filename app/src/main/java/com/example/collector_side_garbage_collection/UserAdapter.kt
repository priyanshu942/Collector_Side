package com.example.collector_side_garbage_collection
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class UserAdapter(
    private var userList: List<User>,
    private val onItemClick: (String, String) -> Unit
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userIdTextView: TextView = itemView.findViewById(R.id.userIdTextView)
        val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.userIdTextView.text = user.userId
        holder.userNameTextView.text = user.name

        holder.itemView.setOnClickListener {
            Log.d("UserAdapter", "User clicked: ${user.userId}, Image URL: ${user.imageData?.imageUrl}")
            onItemClick(user.userId, user.imageData!!.imageUrl!!)
        }
    }

    override fun getItemCount(): Int = userList.size

    fun updateData(newList: List<User>) {
        userList = newList
        notifyDataSetChanged()
    }
}
