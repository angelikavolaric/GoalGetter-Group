import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import {KarticeSeznamiComponent} from './kartice/karticeSeznami.component';
import {KarticeComponent} from './kartice/kartice.component'; // For routing

@Component({
  selector: 'prpo-app',
  standalone: true,  // Declare this as a standalone component
  imports: [RouterModule, KarticeSeznamiComponent, KarticeComponent],  // Import necessary modules here (like RouterModule for routing)
  template: `
    <nav>
      <ul>
        <li><a routerLink="/kartice">Kartica</a></li>
        <li><a routerLink="/karticeSeznami">Kartice Seznami</a></li>
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
