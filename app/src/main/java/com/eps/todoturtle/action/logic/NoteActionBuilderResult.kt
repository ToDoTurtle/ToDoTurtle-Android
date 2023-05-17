package com.eps.todoturtle.action.logic

sealed interface NoteActionBuilderResult

class Success(val noteAction: NoteAction) : NoteActionBuilderResult
class Error(val errors: Collection<NoteActionBuilderError>) : NoteActionBuilderResult
