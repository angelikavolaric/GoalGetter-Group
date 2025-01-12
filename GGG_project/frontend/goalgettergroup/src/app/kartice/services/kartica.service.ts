import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Kartica } from '../models/kartica';  // Assuming you have a Kartica model
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class KarticaService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  private url = 'http://localhost:8081/v1/kartice';  // Update the endpoint to work with 'kartice'

  constructor(private http: HttpClient) {}

  // Get all kartice
  getKartice(): Observable<Kartica[]> {
    return this.http.get<Kartica[]>(this.url)
      .pipe(catchError(this.handleError));
  }

  getKarticeFromUser(): Observable<Kartica[]> {
    return this.http.get<Kartica[]>(this.url)
      .pipe(catchError(this.handleError));
  }

  // Get a single kartica by ID
  getKartica(id: number): Observable<Kartica> {
    const url = `${this.url}/${id}`;
    return this.http.get<Kartica>(url)
      .pipe(catchError(this.handleError));
  }

  // Delete a kartica
  deleteKartica(id: number): Observable<number> {
    const url = `${this.url}/${id}`;
    return this.http.delete<number>(url, {headers: this.headers})
      .pipe(catchError(this.handleError));
  }

  // Create a new kartica
  createKartica(kartica: Kartica): Observable<Kartica> {
    return this.http.post<Kartica>(this.url, JSON.stringify(kartica), {headers: this.headers})
      .pipe(catchError(this.handleError));
  }

  // Update an existing kartica
  updateKartica(id: number, kartica: Kartica): Observable<Kartica> {
    if (kartica.id) {
      // Update the existing kartica if id exists
      const url = `${this.url}/${kartica.id}`;
      return this.http.put<Kartica>(url, JSON.stringify(kartica), {headers: this.headers})
        .pipe(catchError(this.handleError));
    } else {
      // Add a new kartica if id doesn't exist
      return this.http.post<Kartica>(this.url, JSON.stringify(kartica), {headers: this.headers})
        .pipe(catchError(this.handleError));
    }
  }

  // Handle any HTTP errors
  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
}
