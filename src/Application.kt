package com.example

import com.example.plugins.configureRouting
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*


fun main(){
    embeddedServer(Netty,port = 8080,host = "0.0.0.0"){
        configureRouting()
        install(ContentNegotiation){
              json()
        }

        /*database.insert(NotesEntity){
            set(it.note,"Study Ktor")
        }
         GETNOTES
        val notes =database.from(NotesEntity)
            .select()
        for (row in notes){
            println("${row[NotesEntity.note]}")
        }
        UPDATE
        database.update(NotesEntity){
        set(it.note,"Update content")
        where{it.id eq 5}
        }
        DELETE
        database.delete(NotesEntity){
        it.id eq 5
        }
          */
    }.start(wait = true)
}

