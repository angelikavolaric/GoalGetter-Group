import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';  // For common directives like ngIf, ngFor
import { RouterModule, Router } from '@angular/router';  // For routing
import { DeljenjeService } from './services/deljenje.service';  // Import your Deljenje service
import { Cilj } from './models/cilj';  // Import the Cilj model
import { ActivatedRoute } from '@angular/router';  // For activated route
import { FormsModule } from '@angular/forms';  // For forms

@Component({
  selector: 'app-deljenje',
  standalone: true,  // Mark as standalone component
  imports: [CommonModule, RouterModule, FormsModule],  // Import any common and routing modules
  templateUrl: './deljenje.component.html',  // Make sure the template path is correct
  providers: [DeljenjeService]
})
export class DeljenjeComponent implements OnInit {
  deljenja: Cilj[] = [];  // Initialize the array for deljenje
  deljenje?: Cilj;  // Single Cilj object for handling a specific deljenje
  urlId: string = '';  // URL parameter for deljenje ID

  constructor(
    private deljenjeService: DeljenjeService,  // Inject Deljenje service
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    console.log('DeljenjeComponent loaded');
    this.route.paramMap.subscribe((params) => {
      this.urlId = params.get('id') || '';  // Get the ID from route parameters
      if (this.urlId === '') {
        this.getDeljenja();  // Fetch all deljenje
      } else {
        this.getDeljenje(this.urlId);  // Fetch a specific deljenje by ID
      }
    });
  }

  // Get all deljenje (sharing items)
  getDeljenja(): void {
    this.deljenjeService.getDeljenja().subscribe(
      (deljenja) => {
        this.deljenja = deljenja;  // Assign fetched deljenje to the array
        console.log(this.deljenja);
      },
      (error) => {
        console.error('Error fetching deljenje:', error);  // Handle errors
      }
    );
  }

  // Get a single deljenje by ID
  getDeljenje(id: string): void {
    this.deljenjeService.getDeljenje(Number(this.urlId)).subscribe(
      (deljenje) => {
        this.deljenje = deljenje;  // Assign the fetched deljenje to the component property
      },
      (error) => {
        console.error('Error fetching deljenje:', error);
      }
    );
  }

  // Delete a deljenje (sharing item)
  deleteDeljenje(deljenje: Cilj): void {
    this.deljenjeService.deleteDeljenje(deljenje.id).subscribe(() => {
      this.deljenja = this.deljenja.filter((d) => d.id !== deljenje.id);  // Remove the deleted deljenje from the list
    });
  }

  // Create a new deljenje (sharing item)
  addDeljenje(deljenje: Cilj): void {
    this.deljenjeService.createDeljenje(deljenje).subscribe((newDeljenje) => {
      this.deljenja.push(newDeljenje);  // Add the newly created deljenje to the list
    });
  }

  // Update an existing deljenje (sharing item)
  updateDeljenje(deljenje: Cilj): void {
    this.deljenjeService.updateDeljenje(deljenje.id, deljenje).subscribe((updatedDeljenje) => {
      const index = this.deljenja.findIndex((d) => d.id === updatedDeljenje.id);
      if (index !== -1) {
        this.deljenja[index] = updatedDeljenje;  // Update the deljenje in the list
      }
    });
  }
}
