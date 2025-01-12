import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {KarticeSeznamiComponent} from './kartice/karticeSeznami.component';
//import {SeznamPodrobnostiComponent} from './seznami/seznam-podrobnosti.component';
//import { ArtikelDodajComponent } from './seznami/artikel-dodaj.component';

export const routes: Routes = [
  {path: '', redirectTo: '/kartice', pathMatch: 'full'},
  {path: 'kartice', component: KarticeSeznamiComponent},
  //{path: 'seznami/:id', component: SeznamPodrobnostiComponent},
  //{path: 'seznami/:id/dodaj', component: ArtikelDodajComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
