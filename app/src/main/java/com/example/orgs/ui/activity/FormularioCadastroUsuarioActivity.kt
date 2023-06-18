package com.example.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityFormularioCadastroUsuarioBinding
import com.example.orgs.model.Usuario

class FormularioCadastroUsuarioActivity : AppCompatActivity() {
    private val dao by lazy {
        AppDatabase.getConnection(this).usuatioDao()
    }

    private val binding by lazy {
        ActivityFormularioCadastroUsuarioBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()
    }

    private fun configuraBotaoCadastrar() {
        binding.activityFormularioCadastroBotaoCadastrar.setOnClickListener {
            val novoUsuario = criaUsuario()
            Log.i("asdqwe", "onCreate: $novoUsuario")
            dao.salva(novoUsuario)
            finish()
        }
    }

    private fun criaUsuario(): Usuario {
        val usuario = binding.activityFormularioCadastroUsuario.text.toString()
        val nome = binding.activityFormularioCadastroNome.text.toString()
        val senha = binding.activityFormularioCadastroSenha.text.toString()
        return Usuario(usuario, nome, senha)
    }
}