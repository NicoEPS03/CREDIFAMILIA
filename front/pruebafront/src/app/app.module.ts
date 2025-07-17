import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MaterialModule } from './_material/material/material.module';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { ClientComponent } from './views/client/client.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { InsertClientComponent } from './views/client/insert-client/insert-client.component';
import { DeleteClientComponent } from './views/client/delete-client/delete-client.component';

@NgModule({
  declarations: [
    AppComponent,
    ClientComponent,
    InsertClientComponent,
    DeleteClientComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule 
  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
