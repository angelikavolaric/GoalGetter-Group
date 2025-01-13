// src/app/app-routing.module.ts

import { Routes } from '@angular/router';
import { KarticeSeznamiComponent } from './kartice/karticeSeznami.component'; // Import your components
import { KarticeComponent } from './kartice/kartice.component';
import {UporabnikiComponent} from './Uporabniki/uporabniki.component';
import {DeljenjeComponent} from './deljenje/deljenje.component';
import {CasovnikComponent} from './casovnik/casovnik.component';

export const routes: Routes = [
  { path: '', component: KarticeSeznamiComponent },  // Set up the default route
  { path: 'karticeSeznam', component: KarticeSeznamiComponent },
  { path: 'karticeSeznam/:id', component: KarticeSeznamiComponent },
  { path: 'kartice', component: KarticeComponent },
  { path: 'kartica/:id', component: KarticeComponent },
  { path: 'uporabniki', component: UporabnikiComponent },
  { path: 'uporabniki/:id', component: UporabnikiComponent  },
  { path: 'cilji', component: DeljenjeComponent  },
  { path: 'cilji/:id', component: DeljenjeComponent  },
  { path: 'casovnik', component: CasovnikComponent  },
  { path: 'casovnik/:id', component: CasovnikComponent  },

  { path: '**', redirectTo: '' }
];

