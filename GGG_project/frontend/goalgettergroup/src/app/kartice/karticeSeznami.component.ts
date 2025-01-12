import { Component, OnInit } from '@angular/core';
import { Router, Routes } from '@angular/router';


import { KarticeSeznam } from './models/karticeSeznam';
import { KarticeService } from './services/kartice.service';

@Component({
  //moduleId: module.id,
  selector: 'vsi-seznami',
  standalone: true,
  templateUrl: 'kar<ticeSeznami.component.html'
})
export class KarticeSeznamiComponent implements OnInit {
  seznami?: KarticeSeznam[] = [];  // Initialize the array for lists of "kartice seznam"
  seznam?: KarticeSeznam;  // Individual "kartice seznam" object

  // Inject services in the constructor
  constructor(private karticeService: KarticeService,
              private router: Router) { }

  // ngOnInit lifecycle hook for initialization
  ngOnInit(): void {
    this.getSeznami();  // Fetch the list of kartice seznams when the component is initialized
  }

  // Fetch all kartice seznams
  getSeznami(): void {
    this.karticeService
      .getSeznami()
      .subscribe(seznami => this.seznami = seznami);  // Assign the fetched data to the `seznami` property
  }

  // specifičen seznam
  naSeznam(seznam: KarticeSeznam): void {
    this.seznam = seznam;
    this.router.navigate(['/seznami', this.seznam.id]);
  }

  // Delete a kartice seznam
  delete(seznam: KarticeSeznam): void {
    this.karticeService
      .deleteSeznam(seznam.id)
      .subscribe(() => {
        // Filter out the deleted seznam from the array
        this.seznami = this.seznami?.filter(s => s.id !== seznam.id);
      });
  }
}
