package com.example.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.orgs.model.Produto
import com.example.orgs.model.Usuario

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun salva(vararg usuario: Usuario)

    @Query("SELECT * FROM Usuario WHERE id = :id")
    fun buscaPorId(id: String) : Usuario?

    @Query("SELECT * FROM Usuario WHERE nome = :nome AND senha = :senha")
    fun authentica(nome: String, senha: String): Usuario?

}