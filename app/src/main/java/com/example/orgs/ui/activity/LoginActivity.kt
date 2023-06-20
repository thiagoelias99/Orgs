package com.example.orgs.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.orgs.R
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityLoginBinding
import com.example.orgs.extensions.toast
import com.example.orgs.extensions.vaiPara

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val dao by lazy {
        AppDatabase.getConnection(this).usuatioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()
        configuraBotaoEntrar()

        val sharedPrefs = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        sharedPrefs.getString("CHAVE_USER", null)?.let {
            Intent(this, ListaProdutosActivity::class.java).run {
                startActivity(this)
            }
        }
    }

    private fun configuraBotaoEntrar() {
        binding.activityLoginBotaoEntrar.setOnClickListener {
            val usuario = binding.activityLoginUsuario.text.toString()
            val senha = binding.activityLoginSenha.text.toString()
            Log.i("telias-debug", "onCreate: $usuario - $senha")
            dao.authentica(usuario, senha)?.let { usuario ->
                Intent(this, ListaProdutosActivity::class.java).run {
                    val sharedPrefs = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPrefs.edit()
                    editor.apply {
                        putString("CHAVE_USER", usuario.id)
                    }.apply()
                    startActivity(this)
                }
            } ?: toast("Usúario errado!")
        }
    }

    private fun configuraBotaoCadastrar() {
        binding.activityLoginBotaoCadastrar.setOnClickListener {
            vaiPara(FormularioCadastroUsuarioActivity::class.java)
        }
    }

}