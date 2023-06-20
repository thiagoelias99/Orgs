package com.example.orgs.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.R

import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityListaProdutosActivityBinding
import com.example.orgs.model.Produto
import com.example.orgs.model.Usuario

import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "telias-debug"

class ListaProdutosActivity : AppCompatActivity() {

    private val produtoDao by lazy {
        AppDatabase.getConnection(this).produtoDao()
    }
    private val usuarioDao by lazy {
        AppDatabase.getConnection(this).usuatioDao()
    }
    private val adapter by lazy {
        ListaProdutosAdapter(context = this, produtos = produtoDao.buscaTodos())
    }
    private val binding by lazy {
        ActivityListaProdutosActivityBinding.inflate(layoutInflater)
    }
    private val scope = CoroutineScope(Dispatchers.Main)

    private var userId: String? = null

//    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
        userId = intent.getStringExtra("CHAVE_USER")
//        val sharedPrefs = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
//        userId = sharedPrefs.getString("CHAVE_USER", null)
    }

    override fun onResume() {
        super.onResume()

        scope.launch {
            val produtos = produtoDao.buscaTodosDoUsuario(userId ?: "")
            adapter.atualiza(produtos)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val produtosOrdenado: List<Produto>? = when (item.itemId) {
//            R.id.menu_lista_produtos_ordenar_nome_asc ->
//                produtoDao.buscaTodosOrdenadorPorNomeAsc()
//            R.id.menu_lista_produtos_ordenar_nome_desc ->
//                produtoDao.buscaTodosOrdenadorPorNomeDesc()
//            R.id.menu_lista_produtos_ordenar_descricao_asc ->
//                produtoDao.buscaTodosOrdenadorPorDescricaoAsc()
//            R.id.menu_lista_produtos_ordenar_descricao_desc ->
//                produtoDao.buscaTodosOrdenadorPorDescricaoDesc()
//            R.id.menu_lista_produtos_ordenar_valor_asc ->
//                produtoDao.buscaTodosOrdenadorPorValorAsc()
//            R.id.menu_lista_produtos_ordenar_valor_desc ->
//                produtoDao.buscaTodosOrdenadorPorValorDesc()
//            R.id.menu_lista_produtos_ordenar_sem_ordem ->
//                produtoDao.buscaTodos()
//            else -> null
//        }
//        produtosOrdenado?.let {
//            adapter.atualiza(it)
//        }

        when(item.itemId){
            R.id.logout -> {
                val sharedPrefs = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val editor = sharedPrefs.edit()
                editor.apply {
                    putString("CHAVE_USER", null)
                }.apply()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configuraFab() {
        val fab = binding.activityListaProdutosFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutosRecyclerView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalhesProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
        adapter.quandoClicaEmEditar = {
            Log.i(TAG, "configuraRecyclerView: Editar $it")
        }
        adapter.quandoClicaEmRemover = {
            Log.i(TAG, "configuraRecyclerView: Remover $it")
        }
    }
}