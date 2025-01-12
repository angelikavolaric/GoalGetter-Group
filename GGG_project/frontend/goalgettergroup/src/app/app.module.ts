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


@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    KarticeComponent,
    AppComponent,
    KarticeSeznamiComponent,

  ],
  declarations: [


  ],
  providers: [KarticeService, KarticaService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

