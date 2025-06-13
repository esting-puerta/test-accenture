import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Observable } from 'rxjs';
import { TodoService } from '../../../services/todo.service';
import { ToastService } from 'src/app/services/toast.service';
import { Tarea } from '../../../models/tarea.model';

interface Categoria {
  id: number;
  nombre: string;
}

@Component({
  selector: 'app-todo-list-form',
  templateUrl: './todo-list-form.component.html',
  styleUrls: ['./todo-list-form.component.scss'],
  standalone: false,
})
export class TodoListFormComponent implements OnInit {

  nuevaTarea: Omit<Tarea, 'id' | 'fechaCreacion' | 'fechaActualizacion'> = {
    titulo: '',
    descripcion: '',
    categoriaId: 0,
    completada: false
  };

  categorias$: Observable<Categoria[]>;
  @Output() return = new EventEmitter();

  constructor(private todoService: TodoService, private toastService: ToastService) {
    this.categorias$ = this.todoService.getCategorias();
  }

  ngOnInit() {}

  agregarTarea() {
    if (this.nuevaTarea.titulo && this.nuevaTarea.categoriaId) {
      this.todoService.agregarTarea(this.nuevaTarea).subscribe({
        next: () => {
          // Limpiar el formulario después de agregar
          this.nuevaTarea = {
            titulo: '',
            descripcion: '',
            categoriaId: 0,
            completada: false
          };
          this.toastService.showSuccess(' Tarea agregada!');
        },
        error: (error: Error) => {
          console.error('Error al agregar la tarea:', error);
        }
      });
    }
  }

  goBack() {
    this.return.emit();
  }
}
