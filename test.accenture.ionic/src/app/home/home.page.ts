import { Component, OnInit } from '@angular/core';
import { RemoteConfigService } from '../services/remote-config.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {
  featureEnabled = false;
  welcomeMessage = '';
  maxRetries = 0;

  constructor(private remoteConfigService: RemoteConfigService) {}

  ngOnInit() {
    // Ejemplo de uso de Remote Config
    this.remoteConfigService.getBoolean('feature_enabled').subscribe(
      value => this.featureEnabled = value
    );

    this.remoteConfigService.getString('welcome_message').subscribe(
      value => this.welcomeMessage = value
    );

    this.remoteConfigService.getNumber('max_retries').subscribe(
      value => this.maxRetries = value
    );
  }
} 