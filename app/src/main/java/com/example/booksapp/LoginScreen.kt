package com.example.booksapp

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

class LoginScreen : Fragment() {

    companion object {
        fun newInstance() = LoginScreen()
    }

    private lateinit var viewModel: LoginScreenViewModel
    private lateinit var v : View
    private lateinit var userWritten: EditText
    private lateinit var passWritten: EditText
    private lateinit var confirmButton: Button
    private lateinit var signInButton: Button

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_login_screen, container, false)
        userWritten = v.findViewById(R.id.editUser)
        passWritten = v.findViewById(R.id.editPass)
        confirmButton = v.findViewById(R.id.loginButton)
        signInButton = v.findViewById(R.id.createUserButton)
        return v
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(LoginScreenViewModel::class.java)

        viewModel.usersList.add(User("admin", "admin"))
        viewModel.usersList.add(User("isabella", "isalol"))
        viewModel.usersList.add(User("sol", "sollol"))

        confirmButton.setOnClickListener {
            val myUser: String = userWritten.text.toString()
            val myPassword: String = passWritten.text.toString()

            var userFound: User? = viewModel.usersList.find { u -> u.username == myUser }

            if (userFound != null) {
                if (userFound.password == myPassword) {
                    Snackbar.make(v, "Login successful", Snackbar.LENGTH_SHORT).show()
                    view?.findNavController()?.navigate(R.id.action_loginScreen_to_bookListFragment)
                } else {
                    Snackbar.make(v, "Username or password incorrect", Snackbar.LENGTH_SHORT).show()
                }
            } else {
                Snackbar.make(v, "Username and password does not exist", Snackbar.LENGTH_SHORT)
                    .show()
            }

            if (myUser.isEmpty() && myPassword.isEmpty()) {
                Snackbar.make(v, "Please insert your Username and Password", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        signInButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_loginScreen_to_createUserFragment)
        }
    }
}