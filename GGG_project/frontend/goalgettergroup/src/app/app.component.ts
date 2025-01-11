import {Component} from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

@Component({
  //moduleId: module.id,
  selector: 'prpo-app',
  template: `
        <h1>{{naslov}}</h1>
        <router-outlet></router-outlet>
    `
})
export class AppComponent {
  naslov = 'Goal getter group';
  title = 'Goal getter group';
}
