import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import { routes } from './app.routes';

import {AppComponent} from './app.component';
import {KarticeSeznamiComponent} from './kartice/karticeSeznami.component';

import {KarticeService} from './kartice/services/kartice.service';
import {KarticeComponent} from './kartice/kartice.component';
import {KarticaService} from './kartice/services/kartica.service';
import {UporabnikiComponent} from './Uporabniki/uporabniki.component';
import {UporabnikiService} from './Uporabniki/services/uporabniki.service';
import {DeljenjeService} from './deljenje/services/deljenje.service';
import {DeljenjeComponent} from './deljenje/deljenje.component';
import {CasovnikService} from './casovnik/services/casovnik.service';


@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    KarticeComponent,
    AppComponent,
    KarticeSeznamiComponent,
    UporabnikiComponent,
    KarticeComponent,
    DeljenjeComponent,
    CasovnikService,
  ],
  declarations: [


  ],
  providers: [KarticeService, KarticaService, UporabnikiService, DeljenjeService, CasovnikService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

