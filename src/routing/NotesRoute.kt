package com.example.routing

import com.example.db.DatabaseConnection
import com.example.db.DatabaseConnection.database
import com.example.entities.NotesEntity
import com.example.models.Note
import com.example.models.NoteRequest
import com.example.models.NoteResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.ktorm.dsl.*


fun Application.notesRoutes(){
    val db=DatabaseConnection.database

    routing {
        get("/notes"){
            val notes=db.from(NotesEntity).select()
                .map {
                    val id=it[NotesEntity.id]
                    val note=it[NotesEntity.note]
                    Note(id ?: -1,note ?:"")
                }
            call.respond(notes)
        }
        get("/notes/{id}"){
            val id = call.parameters["id"]?.toInt() ?: -1
            val note=db.from(NotesEntity)
                .select()
                .where { NotesEntity.id eq id }
                .map{
                    val id=it[NotesEntity.id]!!
                    val note=it[NotesEntity.note]!!
                    Note(id=id,note=note)
                }.firstOrNull()

                if(note==null){
                    call.respond(HttpStatusCode.NotFound,
                    NoteResponse(
                        success = false,
                        data="Could not found note with id = $id"
                    ) )
                }else{
                    call.respond(HttpStatusCode.OK,
                    NoteResponse(
                        success = true,
                        data=note
                    ))
                }
        }
        post("/notes"){
            val request=call.receive<NoteRequest>()
            val result=db.insert(NotesEntity){
                set(it.note,request.note)
            }
            if(result==1){
                call.respond(HttpStatusCode.OK,NoteResponse(
                    success = true,
                    data="Values has been inserted successfully"
                ))
            }else{
                call.respond(HttpStatusCode.BadRequest,NoteResponse(
                    success = false,
                    data="Failed to insert values"
                ))
            }
        }
        put("/notes/{id}"){
            val id=call.parameters["id"]?.toInt()?:-1
            val updateNote= call.receive<NoteRequest>()
            val rowsEffect= db.update(NotesEntity){
                set(it.note,updateNote.note)
                where{
                    it.id eq id
                }
            }
            if(rowsEffect==1){
                call.respond(
                    HttpStatusCode.OK,
                    NoteResponse(
                        success = true,
                        data = "Note updated"
                    )
                )
            }else{
                call.respond(
                    HttpStatusCode.BadRequest,
                    NoteResponse(
                        success = false,
                        data = "Note updating failed"
                    )
                )
            }
        }
        put("/notes/{id}"){
            val id=call.parameters["id"]?.toInt()?:-1
            val updateNote= call.receive<NoteRequest>()
            val rowsEffect= db.update(NotesEntity){
                set(it.note,updateNote.note)
                where{
                    it.id eq id
                }
            }
            if(rowsEffect==1){
                call.respond(
                    HttpStatusCode.OK,
                    NoteResponse(
                        success = true,
                        data = "Note updated"
                    )
                )
            }else{
                call.respond(
                    HttpStatusCode.BadRequest,
                    NoteResponse(
                        success = false,
                        data = "Note updating failed"
                    )
                )
            }
        }
        delete("/notes/{id}"){
            val id=call.parameters["id"]?.toInt() ?:-1
            val rowsEffect= db.delete(NotesEntity){
              it.id eq id
            }
            if(rowsEffect==1){
                call.respond(
                    HttpStatusCode.OK,
                    NoteResponse(
                        success = true,
                        data = "Note deleted"
                    )
                )
            }else{
                call.respond(
                    HttpStatusCode.BadRequest,
                    NoteResponse(
                        success = false,
                        data = "Note deleting failed"
                    )
                )
            }
        }



    }
}