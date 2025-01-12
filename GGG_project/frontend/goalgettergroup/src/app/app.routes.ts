// src/app/app-routing.module.ts

import { Routes } from '@angular/router';
import { KarticeSeznamiComponent } from './kartice/karticeSeznami.component'; // Import your components
import { KarticeComponent } from './kartice/kartice.component';

export const routes: Routes = [
  { path: '', component: KarticeSeznamiComponent },  // Set up the default route
  { path: 'karticeSeznam', component: KarticeSeznamiComponent },
  { path: 'kartice', component: KarticeComponent },
  { path: 'kartica/:id', component: KarticeComponent },

  { path: '**', redirectTo: '' }
];

