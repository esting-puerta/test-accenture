module.exports = {
  preset: 'jest-preset-angular',
  setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],
  globalSetup: 'jest-preset-angular/global-setup',
  testPathIgnorePatterns: [
    '<rootDir>/node_modules/',
    '<rootDir>/dist/',
  ],
  moduleNameMapper: {
    '^src/(.*)$': '<rootDir>/src/$1',
  },
  transformIgnorePatterns: [
    'node_modules/(?!(@angular|@ionic|@stencil|@capacitor|ionicons)/)'
  ],
  collectCoverageFrom: [
    'src/app/**/*.ts',
    '!src/app/**/*.module.ts',
    '!src/app/**/*.array.ts',
    '!src/app/**/*.constant.ts',
    '!src/app/**/*.enum.ts',
    '!src/app/**/*.interface.ts',
    '!src/app/**/*.model.ts',
    '!src/app/**/*.pipe.ts',
    '!src/app/**/*.spec.ts',
  ],
  coverageDirectory: 'coverage',
  coverageReporters: ['html', 'text-summary'],
  testMatch: ['**/*.spec.ts'],
  transform: {
    '^.+\\.(ts|js|mjs|html|svg)$': [
      'jest-preset-angular',
      {
        tsconfig: '<rootDir>/tsconfig.spec.json',
        stringifyContentPathRegex: '\\.html$',
        useESM: true
      }
    ]
  }
}; 