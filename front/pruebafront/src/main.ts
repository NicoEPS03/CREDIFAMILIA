import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideNativeDateAdapter } from '@angular/material/core';

bootstrapApplication(AppComponent, {
  providers: [
    provideNativeDateAdapter(), // ✅ Necesario para Datepicker
  ]
});


platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
