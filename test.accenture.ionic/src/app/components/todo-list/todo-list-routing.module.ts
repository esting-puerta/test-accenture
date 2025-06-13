import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TodoListComponent } from './todo-list.component';
import { TodoListFormComponent } from './todo-list-form/todo-list-form.component';

const routes: Routes = [
  {
    path: '',
    component: TodoListComponent,
    pathMatch: 'full',
  },
  {
    path: 'form-tareas',
    component: TodoListFormComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TodoListRoutingModule { }
