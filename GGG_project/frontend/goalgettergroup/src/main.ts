// src/main.ts

import { bootstrapApplication } from '@angular/platform-browser';
import {ApplicationConfig, importProvidersFrom, provideZoneChangeDetection} from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';  // Import your routes
import { AppComponent } from './app/app.component'; // Import your AppComponent
import { KarticeService } from './app/kartice/services/kartice.service';
import {KarticaService} from './app/kartice/services/kartica.service'; // Import services
import {HttpClientModule, provideHttpClient} from '@angular/common/http';

// Standalone app configuration
export const appConfig: ApplicationConfig = {
  providers: [
    //provideZoneChangeDetection({ eventCoalescing: true }),  // Enable efficient change detection
    provideRouter(routes),  // Provide the router with the routes configuration
    KarticeService,  // Provide the KarticeService
    KarticaService
  ]
};
bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(),
    ...appConfig.providers
  ]
})
  .catch((err) => console.error(err));
