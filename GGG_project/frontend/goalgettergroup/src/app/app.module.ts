import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

import {AppRoutingModule} from './app.routes.module';
import { RouterModule, Routes } from '@angular/router';

import {AppComponent} from './app.component';
import {KarticeSeznamiComponent} from './kartice/karticeSeznami.component';
//import {ArtikelDodajComponent} from './seznami/artikel-dodaj.component';
//import {SeznamPodrobnostiComponent} from './seznami/seznam-podrobnosti.component';
import {KarticeService} from './kartice/services/kartice.service';


@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    KarticeSeznamiComponent
  ],
  declarations: [
    AppComponent,

    //SeznamPodrobnostiComponent,
    //ArtikelDodajComponent
  ],
  providers: [KarticeService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

