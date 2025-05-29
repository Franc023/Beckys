import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { RouterOutlet, RouterModule } from '@angular/router';
import { CommonModule, isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'Beckys Floristería';
  currentYear: number = new Date().getFullYear();

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  ngOnInit(): void {
    // Cualquier inicialización necesaria
  }
  
  isLoggedIn(): boolean {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem('token') !== null;
    }
    return false;
  }
  
  logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('token');
      window.location.href = '/';
    }
  }
}
