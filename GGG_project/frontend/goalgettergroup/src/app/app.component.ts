import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import {KarticeSeznamiComponent} from './kartice/karticeSeznami.component';
import {KarticeComponent} from './kartice/kartice.component'; // For routing
import {UporabnikiComponent} from './Uporabniki/uporabniki.component';
import {DeljenjeComponent} from './deljenje/deljenje.component';
import {CasovnikComponent} from './casovnik/casovnik.component';

@Component({
  selector: 'prpo-app',
  standalone: true,  // Declare this as a standalone component
  imports: [RouterModule, KarticeSeznamiComponent, KarticeComponent, UporabnikiComponent, DeljenjeComponent, CasovnikComponent],  // Import necessary modules here (like RouterModule for routing)
  template: `
    <nav>
      <h1>Goal getter group</h1>
      <ul>
        <li><a routerLink="/kartice">Kartica</a></li>
        <li><a routerLink="/karticeSeznami">Kartice Seznami</a></li>
        <li><a routerLink="/uporabniki">Uporabniki Seznami</a></li>
        <li><a routerLink="/cilji">Cilji</a></li>
        <li><a routerLink="/casovnik">Casovnik</a></li>
      </ul>
    </nav>
      <router-outlet></router-outlet>
      <!-- This is where routed components will be displayed -->
  `
})
export class AppComponent implements OnInit {
  ngOnInit(): void {
    // Initialization logic here
  }
}
