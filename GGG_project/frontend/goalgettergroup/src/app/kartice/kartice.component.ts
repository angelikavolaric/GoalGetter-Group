import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';  // For common directives like ngIf, ngFor
import { RouterModule, Router } from '@angular/router';  // For routing
import { KarticaService } from './services/kartica.service';  // Import your service
import { Kartica } from './models/kartica';  // Import the Kartica model
import { HttpClient } from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'kartica',
  standalone: true,  // Mark as standalone component
  imports: [CommonModule, RouterModule, FormsModule,],  // Import any common and routing modules
  templateUrl: './kartice.component.html',  // Make sure path is correct
})
export class KarticeComponent implements OnInit {
  kartice: Kartica[] = [];  // Initialize the array for kartice
  kartica?: Kartica;  // Single Kartica object for handling specific kartica
  vidnoVprasanje: { [key: number]: boolean } = {};
  urlId: string = ""
  constructor(
    private karticaService: KarticaService,
    private router: Router,
  private route: ActivatedRoute, ) {}

  ngOnInit(): void {
    console.log('KarticaComponent loaded');
    this.route.paramMap.subscribe(params => {this.urlId = params.get('id') || '';
      if(this.urlId == '') {
        this.getKartice();
      } else {
        this.getKartica(this.urlId)
      }
    })
  }


  getKartice(): void {
    this.karticaService.getKartice().subscribe(
        (kartice) => {
          this.kartice = kartice;  // Correctly assign the fetched kartice to the component's array
          console.log(this.kartice)
        },
        (error) => {
          console.error('Error fetching kartice:', error);  // Handle any error that occurs during the HTTP request
        }
      );
  }


  getKartica(id: string): void {
    this.karticaService.getKartica(Number(this.urlId)).subscribe(
      (k) => {
        this.kartica = k;
      },
      (error) => {
        console.error('Error fetching kartica:', error);
      }
    );
  }

  // Delete a kartica
  deleteKartica(kartica: Kartica): void {
    this.karticaService
      .deleteKartica(kartica.id)  // Call the delete service method
      .subscribe(() => {
        // Filter out the deleted kartica from the kartice array
        this.kartice = this.kartice.filter((k) => k.id !== kartica.id);
      });
  }

  // Create a new kartica (add kartica)
  addKartica(newKartica: Kartica): void {
    this.karticaService
      .createKartica(newKartica)  // Call the create service method
      .subscribe((kartica) => {
        this.kartice.push(kartica);  // Add the newly created kartica to the kartice array
      });
  }

  // Update an existing kartica
  updateKartica(kartica: Kartica): void {
    this.karticaService
      .updateKartica(kartica)  // Call the update service method
      .subscribe((updatedKartica) => {
        const index = this.kartice.findIndex(k => k.id === updatedKartica.id);
        if (index !== -1) {
          this.kartice[index] = updatedKartica;  // Update the kartica in the array
        }
      });
  }
  toggleAnswerVisibility(kartica: Kartica): void {
    this.vidnoVprasanje[kartica.id] = !this.vidnoVprasanje[kartica.id];  // Toggle visibility
  }
}
