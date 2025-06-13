import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TodoListRoutingModule } from './todo-list-routing.module';
import { TodoListComponent } from './todo-list.component';
import { IonicModule } from '@ionic/angular';
import { FormsModule } from '@angular/forms';
import { TodoListFilterComponent } from './todo-list-filter/todo-list-filter.component';
import { TodoListFormComponent } from './todo-list-form/todo-list-form.component';


@NgModule({
  declarations: [
    TodoListComponent,
    TodoListFilterComponent,
    TodoListFormComponent
  ],
  imports: [
    CommonModule,
    TodoListRoutingModule,
    IonicModule, 
    FormsModule, 
    CommonModule
  ]
})
export class TodoListModule { }
