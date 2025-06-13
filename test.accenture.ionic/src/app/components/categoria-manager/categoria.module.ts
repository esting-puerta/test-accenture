import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CategoriaRoutingModule } from './categoria-routing.module';
import { CategoriaManagerComponent } from './categoria-manager.component';
import { IonicModule } from '@ionic/angular';
import { FormsModule } from '@angular/forms';
import { CategoriaListComponent } from './categoria-list/categoria-list.component';
import { CategoriaFormComponent } from './categoria-form/categoria-form.component';


@NgModule({
  declarations: [
    CategoriaManagerComponent,
    CategoriaListComponent,
    CategoriaFormComponent
  ],
  imports: [
    CommonModule,
    CategoriaRoutingModule,
    IonicModule, 
    FormsModule, 
    IonicModule
  ]
})
export class CategoriaModule { }
