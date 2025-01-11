/*import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import { KarticeSeznam } from './models/karticeSeznam';
import { KarticeService } from './services/kartice.service';

@Component({
  moduleId: module.id,
  selector: 'vsi-seznami',
  templateUrl: 'karticeSeznami.component.html'
})
export class KarticeSeznamiComponent implements OnInit {
  seznami: KarticeSeznam[];
  seznam: KarticeSeznam;

  constructor(private karticeService: KarticeService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getSeznami();
  }

  getSeznami(): void {
    this.karticeService
      .getSeznami()
      .subscribe(seznami => this.seznami = seznami);
  }

  naPodrobnosti(seznam: KarticeSeznam): void {
    this.seznam = seznam;
    this.router.navigate(['/seznami', this.seznam.id]);
  }

  delete(seznam: KarticeSeznam): void {
    this.karticeService
      .delete(seznam.id)
      .subscribe(seznamId => this.seznami = this.seznami.filter(s => s.id !== seznamId));
  }

}
*/
