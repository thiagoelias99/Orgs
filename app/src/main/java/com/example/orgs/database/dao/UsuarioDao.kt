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

    @Update
    fun altera(usuario: Usuario)

    @Delete
    fun remove(usuario: Usuario)

    @Query("SELECT * FROM Usuario")
    fun buscaTodos() : List<Usuario>

    @Query("SELECT * FROM Usuario WHERE id = :id")
    fun buscaPorId(id: Long) : Usuario?
}