package com.example.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.orgs.database.converter.Converters
import com.example.orgs.database.dao.ProdutoDao
import com.example.orgs.database.dao.UsuarioDao
import com.example.orgs.model.Produto
import com.example.orgs.model.Usuario

@Database(
    entities = [
        Produto::class,
        Usuario::class
    ], version = 2
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao
    abstract fun usuatioDao(): UsuarioDao

    companion object {
        fun getConnection(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "orgs.db"
            ).allowMainThreadQueries().addMigrations(MIGRATION_1_2).build()
        }
    }
}