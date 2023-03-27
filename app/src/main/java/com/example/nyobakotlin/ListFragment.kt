package com.example.nyobakotlin

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nyobakotlin.adapter.UserAdapter
import com.example.nyobakotlin.data.AppDatabase
import com.example.nyobakotlin.data.entity.User
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var createButton : FloatingActionButton
    private lateinit var recyclerView : RecyclerView
    private lateinit var userList : ArrayList<User>
    private lateinit var userAdapter: UserAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        createButton = view.findViewById(R.id.createButton)
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        userList = ArrayList()

        database = AppDatabase.getInstance(requireContext())
        userAdapter = UserAdapter(userList)
        recyclerView.adapter = userAdapter
        userAdapter.setDialog(object : UserAdapter.Dialog{
            override fun onClick(position: Int) {
                // Membuat dialog view
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle(userList[position].projectName)
                dialog.setItems(R.array.items_option, DialogInterface.OnClickListener{dialog, which ->
                    if (which == 0) {
                        // Edit
                        val intent = Intent(requireContext(), EditActivity::class.java)
                        intent.putExtra("id", userList[position].uid)
                        startActivity(intent)
                    } else if (which == 1) {
                        // Hapus
                        database.userDao().delete(userList[position])
                        getData()
                    } else if (which == 2) {
                        // Batal
                        dialog.dismiss()
                    } else {
                        // Lihat
                        val intent = Intent(requireContext(), DetailActivity::class.java)
                        intent.putExtra("id", userList[position].uid)
                        startActivity(intent)
                    }
                })
                // Menampilkan dialog
                val dialogView = dialog.create()
                dialogView.show()
            }
        })

        createButton.setOnClickListener() {
            val intent = Intent(requireContext(), FormActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getData() {
        userList.clear()
        userList.addAll(database.userDao().getAll())
        userAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}