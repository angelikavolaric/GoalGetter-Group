import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';  // For common directives like ngIf, ngFor
import { RouterModule, Router } from '@angular/router';  // For routing
import { UporabnikiService } from './services/uporabniki.service';  // Import your service
import { Uporabnik } from './models/uporabnik';  // Import the Kartica model
import { HttpClient } from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'uporabnik',
  standalone: true,  // Mark as standalone component
  imports: [CommonModule, RouterModule, FormsModule,],  // Import any common and routing modules
  templateUrl: './uporabniki.component.html',  // Make sure path is correct
})
export class UporabnikiComponent implements OnInit {
  uporabniki:Uporabnik[] = [];  // Initialize the array for kartice
  uporabnik?: Uporabnik;  // Single Kartica object for handling specific kartica
  urlId: string = ""
  constructor(
    private uporabnikService: UporabnikiService,
    private router: Router,
  private route: ActivatedRoute, ) {}

  ngOnInit(): void {
    console.log('UporabnikComponent loaded');
    this.route.paramMap.subscribe(params => {this.urlId = params.get('id') || '';
      if(this.urlId == '') {
        console.log("in")
        this.getUporabniki();
      } else {
        this.getUporabnik(this.urlId)
      }
    })
  }


  getUporabniki(): void {
    console.log("in")
    this.uporabnikService.getUporabniki().subscribe(
        (uporabniki) => {
          this.uporabniki = uporabniki;  // Correctly assign the fetched kartice to the component's array
          console.log(this.uporabniki)
        },
        (error) => {
          console.error('Error fetching kartice:', error);  // Handle any error that occurs during the HTTP request
        }
      );
  }


  getUporabnik(id: string): void {
    this.uporabnikService.getUporabnik(Number(this.urlId)).subscribe(
      (k) => {
        this.uporabnik = k;
      },
      (error) => {
        console.error('Error fetching kartica:', error);
      }
    );
  }

  // Delete a kartica
  deleteUporabnik(uporabniki: Uporabnik): void {
    this.uporabnikService
      .deleteUporabnik(uporabniki.id)  // Call the delete service method
      .subscribe(() => {
        // Filter out the deleted kartica from the kartice array
        this.uporabniki = this.uporabniki?.filter((k) => k.id !== uporabniki.id);
      });
  }

  // Create a new kartica (add kartica)
  addUporabnik(uporabnik: Uporabnik): void {
    this.uporabnikService
      .createUporabnik(uporabnik)  // Call the create service method
      .subscribe((uporabnik) => {
        this.uporabniki.push(uporabnik);  // Add the newly created kartica to the kartice array
      });
  }

  // Update an existing kartica
  updateUporabnik(uporabnik: Uporabnik): void {
    this.uporabnikService
      .updateUporabnik(uporabnik.id, uporabnik)  // Call the update service method
      .subscribe((updateduporabnik) => {
        const index = this.uporabniki.findIndex(k => k.id === updateduporabnik.id);
        if (index !== -1) {
          this.uporabniki[index] = updateduporabnik;  // Update the kartica in the array
        }
      });
  }
}
