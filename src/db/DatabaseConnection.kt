package com.example.db

import org.ktorm.database.Database

object DatabaseConnection {

    val database= Database.connect(
        //nombre de la tabla hecha en mysql
        //direccion se cambiara al deployer
        url="jdbc:mysql://localhost:3306/notes",
        driver="com.mysql.cj.jdbc.Driver",
        user="root",
        password="contraMySQL852"
    )
}