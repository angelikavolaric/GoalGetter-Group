import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';  // For common directives like ngIf, ngFor
import { RouterModule, Router } from '@angular/router';  // For routing
import { CasovnikService } from './services/casovnik.service';  // Import your service
import { Casovnik } from './models/casovnik';  // Import the Casovnik model
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'casovnik',
  standalone: true,  // Mark as standalone component
  imports: [CommonModule, RouterModule, FormsModule],  // Import any common and routing modules
  templateUrl: './casovnik.component.html',  // Make sure path is correct
  providers: [CasovnikService]
})
export class CasovnikComponent implements OnInit {
  casovniki: Casovnik[] = [];  // Initialize the array for Casovniks
  casovnik?: Casovnik;  // Single Casovnik object for handling specific Casovnik
  urlId: string = "";  // URL ID from routing
  constructor(
    private casovnikService: CasovnikService,  // Service for interacting with the backend
    private router: Router,  // Router for navigating between views
    private route: ActivatedRoute  // Activated route to capture URL parameters
  ) {}

  ngOnInit(): void {
    console.log('CasovnikComponent loaded');
    this.route.paramMap.subscribe(params => {
      this.urlId = params.get('id') || '';
      if (this.urlId === '') {
        this.getCasovniki();
      } else {
        this.getCasovnik(this.urlId);
      }
    });
  }

  getCasovniki(): void {
    this.casovnikService.getCasovniki().subscribe(
      (casovniki) => {
        this.casovniki = casovniki;  // Assign fetched Casovniks to the component's array
        console.log(this.casovniki);
      },
      (error) => {
        console.error('Error fetching Casovniki:', error);  // Handle any error during the HTTP request
      }
    );
  }

  getCasovnik(id: string): void {
    this.casovnikService.getCasovnik(Number(this.urlId)).subscribe(
      (k) => {
        this.casovnik = k;  // Assign the fetched Casovnik to the component
      },
      (error) => {
        console.error('Error fetching Casovnik:', error);
      }
    );
  }

  // Delete a Casovnik
  deleteCasovnik(casovnik: Casovnik): void {
    this.casovnikService
      .deleteCasovnik(casovnik.id)  // Call the delete service method
      .subscribe(() => {
        // Filter out the deleted Casovnik from the Casovniki array
        this.casovniki = this.casovniki?.filter((k) => k.id !== casovnik.id);
      });
  }

  // Create a new Casovnik (add Casovnik)
  addCasovnik(casovnik: Casovnik): void {
    this.casovnikService
      .createCasovnik(casovnik)  // Call the create service method
      .subscribe((casovnik) => {
        this.casovniki.push(casovnik);  // Add the newly created Casovnik to the Casovniki array
      });
  }

  // Update an existing Casovnik
  updateCasovnik(casovnik: Casovnik): void {
    this.casovnikService
      .updateCasovnik(casovnik.id, casovnik)  // Call the update service method
      .subscribe((updatedCasovnik) => {
        const index = this.casovniki.findIndex(k => k.id === updatedCasovnik.id);
        if (index !== -1) {
          this.casovniki[index] = updatedCasovnik;  // Update the Casovnik in the array
        }
      });
  }
}
