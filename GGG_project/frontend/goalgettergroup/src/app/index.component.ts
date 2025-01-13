import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';  // For common directives like ngIf, ngFor
import { RouterModule, Router } from '@angular/router';  // For routing

import {FormsModule} from '@angular/forms';

@Component({
  selector: 'indeks',
  standalone: true,  // Mark as standalone component
  imports: [CommonModule, RouterModule, FormsModule,],  // Import any common and routing modules
  template: "[]",  // Make sure path is correct
})
export class IndexComponent implements OnInit {
  ngOnInit(): void {
  }
}
