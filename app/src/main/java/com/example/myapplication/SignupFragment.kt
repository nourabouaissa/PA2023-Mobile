import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.SignupViewModel
import com.example.myapplication.User
import com.example.myapplication.services.ApiService

class SignupFragment : DialogFragment() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var profileImageUrlEditText: EditText
    private lateinit var signupButton: Button

    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var signupButton = view.findViewById<Button>(R.id.signupButton)
        var viewModel = ViewModelProvider(requireActivity()).get(SignupViewModel::class.java)

        signupButton.setOnClickListener{
            val username = view.findViewById<EditText>(R.id.username).text.toString().trim()
            val password = view.findViewById<EditText>(R.id.password).text.toString().trim()
            val confirmPassword = view.findViewById<EditText>(R.id.confirmPassword).text.toString().trim()
            val firstName = view.findViewById<EditText>(R.id.firstName).text.toString().trim()
            val lastName = view.findViewById<EditText>(R.id.lastName).text.toString().trim()
            val email = view.findViewById<EditText>(R.id.email).text.toString().trim()
            val profileImageUrl = view.findViewById<EditText>(R.id.profileImageUrl).text.toString().trim()

            // Vérification des champs vides
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
                firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || profileImageUrl.isEmpty()) {
                //Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Vérification de la correspondance entre les mots de passe
            if (password != confirmPassword) {
                //Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Création de l'objet SignupRequest
            val user = User(username, password, confirmPassword, firstName, lastName, email, profileImageUrl)

            viewModel.CreateUser(user);
            dismiss()
        }
    }
}
