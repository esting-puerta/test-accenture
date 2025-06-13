import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Tarea } from '../../../models/tarea.model';
import { Categoria } from '../../../models/categoria.model';
import { TodoService } from '../../../services/todo.service';

@Component({
  selector: 'app-todo-list-filter',
  templateUrl: './todo-list-filter.component.html',
  styleUrls: ['./todo-list-filter.component.scss'],
  standalone: false,
})
export class TodoListFilterComponent implements OnInit {

  tareas$: Observable<Tarea[]>;
  categorias$: Observable<Categoria[]>;
  tareasFiltradas$: Observable<Tarea[]>;
  categoriaSeleccionada: string = 'null';
  @Output() return = new EventEmitter();

  constructor(private todoService: TodoService) {
    this.tareas$ = this.todoService.getTareas();
    this.categorias$ = this.todoService.getCategorias();
    this.tareasFiltradas$ = this.tareas$;
  }

  ngOnInit() {}

  toggleCompletada(tarea: Tarea): void {
    this.todoService.actualizarTarea(tarea);
  }

  eliminarTarea(id: number): void {
    this.todoService.eliminarTarea(id);
  }

  goBack() {
    this.return.emit();
  }

  filtrarPorCategoria(value: string | number | undefined): void {
    if (value === undefined) {
      value = 'null';
    }
    this.categoriaSeleccionada = value.toString();
    const categoriaId = value === 'null' ? null : Number(value);
    this.tareasFiltradas$ = this.tareas$.pipe(
      map(tareas => 
        categoriaId === null 
          ? tareas 
          : tareas.filter(tarea => tarea.categoriaId === categoriaId)
      )
    );
  }
}
