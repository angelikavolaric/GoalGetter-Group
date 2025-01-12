import { KarticeSeznam } from './karticeSeznam';

export interface Kartica {
  id: number;
  vprasanje: string;
  odgovor: string;
  karticeSeznamId: number;

}
