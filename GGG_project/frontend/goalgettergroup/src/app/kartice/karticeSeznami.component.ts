import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';  // For common directives like ngIf, ngFor
import {RouterModule, Router, ActivatedRoute} from '@angular/router';  // For routing
import { KarticeService } from './services/kartice.service';  // Import your service
import { KarticeSeznam} from './models/karticeSeznam';
import { HttpClient} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'kartice-seznami',
  standalone: true,  // Mark as standalone component
  imports: [CommonModule, RouterModule, FormsModule],  // Import any common and routing modules
  templateUrl: './karticeSeznami.component.html',  // Make sure path is correct
})
export class KarticeSeznamiComponent implements OnInit {
  seznami: KarticeSeznam[] = [];  // Initialize the array for lists of "kartice seznam"
  seznam?: KarticeSeznam;  // Individual "kartice seznam" object
  urlId: string = "";
  constructor(private karticeService: KarticeService, private router: Router, private route: ActivatedRoute) {}

  // ngOnInit lifecycle hook for initialization
  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {this.urlId = params.get('id') || '';
      if(this.urlId == '') {
        this.getSeznami();
      } else {
        this.getSeznam(this.urlId)
      }
    })
    this.getSeznami();  // Fetch the list of kartice seznams when the component is initialized
  }

  // Fetch all kartice seznams
  getSeznami(): void {
    this.karticeService.getSeznami().subscribe(
      (seznami) => {
        this.seznami = seznami;  // Correctly assign the fetched kartice to the component's array
        console.log(this.seznami)
      },
      (error) => {
        console.error('Error fetching kartice:', error);  // Handle any error that occurs during the HTTP request
      }
    );
  }

  // Handle click on a specific seznam
  getSeznam(id: string): void {
    this.karticeService.getSeznam(Number(this.urlId)).subscribe(
      (k) => {
        this.seznam = k;
      },
      (error) => {
        console.error('Error fetching kartica:', error);
      }
    );
  }

  // Delete a kartice seznam
  deleteSeznam(seznam: KarticeSeznam): void {
    this.karticeService
      .deleteSeznam(seznam.id)
      .subscribe(() => {
        // Filter out the deleted seznam from the array
        this.seznami = this.seznami.filter((s) => s.id !== seznam.id);
      });
  }
  // Update Seznam
  updateSeznam(seznam: KarticeSeznam): void {
    this.karticeService.updateSeznam(seznam.id, seznam).subscribe((updatedSeznam) => {
      const index = this.seznami.findIndex(s => s.id === updatedSeznam.id);
      if (index !== -1) {
        this.seznami[index] = updatedSeznam;
      }
    });
  }
}
