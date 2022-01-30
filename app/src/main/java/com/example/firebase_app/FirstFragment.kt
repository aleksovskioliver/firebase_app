package com.example.firebase_app

import android.icu.text.CaseMap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.firebase_app.databinding.FragmentFirstBinding
import com.example.firebase_app.model.Post
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    val mAuth = FirebaseAuth.getInstance()

    var database = FirebaseDatabase.getInstance()
    var postReference = database.getReference("posts")

    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etTitle = view.findViewById(R.id.etTitle)
        etDescription = view.findViewById(R.id.etDescription)

        val submitButton: Button = view.findViewById(R.id.etUpload)

        submitButton.setOnClickListener {
            val title: String = etTitle.text.toString()
            val description: String = etDescription.text.toString()

            if(title.isNullOrEmpty() || description.isNullOrEmpty()){
                return@setOnClickListener
            }
            uploadData(title,description)
        }
    }

    private fun uploadData(title: String, description: String) {
        val currentPost = Post(mAuth.uid!!,title,description)

        postReference.push().setValue(currentPost)
            .addOnCompleteListener(OnCompleteListener { task ->
                if(task.isSuccessful) {
                    Toast.makeText(activity, "Success", Toast.LENGTH_LONG).show()
                    etTitle.text.clear()
                    etDescription.text.clear()
                }
                else{
                    Toast.makeText(activity,"ERROR",Toast.LENGTH_LONG).show()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}