.PHONY: help download-dataset clean-dataset setup-dataset

# Dataset URL (Kaggle direct download - requires being logged in or using API)
DATASET_URL := https://www.kaggle.com/api/v1/datasets/download/computingvictor/transactions-fraud-datasets
DATASET_ZIP := dataset/transactions-fraud-datasets.zip

# Default target
help:
	@echo "Dataset Management Makefile"
	@echo ""
	@echo "Available targets:"
	@echo "  make download-dataset  - Download CSV files from Kaggle via curl"
	@echo "  make clean-dataset     - Remove downloaded dataset files"
	@echo "  make setup-dataset     - Full setup: download and extract datasets"
	@echo ""
	@echo "Prerequisites:"
	@echo "  - curl (usually pre-installed on Linux/macOS)"
	@echo "  - unzip (usually pre-installed on Linux/macOS)"
	@echo ""
	@echo "Note: You may need to download manually from:"
	@echo "  https://www.kaggle.com/datasets/computingvictor/transactions-fraud-datasets"
	@echo "  if automatic download fails (Kaggle login required)"

# Download dataset from Kaggle using curl
download-dataset:
	@echo "Creating dataset directory..."
	@mkdir -p dataset
	@echo ""
	@echo "Downloading dataset from Kaggle..."
	@echo "URL: $(DATASET_URL)"
	@echo ""
	@echo "Note: If this fails with authentication error, download manually from:"
	@echo "https://www.kaggle.com/datasets/computingvictor/transactions-fraud-datasets"
	@echo ""
	@curl -L -o $(DATASET_ZIP) "$(DATASET_URL)" || \
		(echo "" && echo "ERROR: Download failed. You may need to:" && \
		 echo "  1. Log in to Kaggle in your browser" && \
		 echo "  2. Download manually from the URL above" && \
		 echo "  3. Place the zip file in dataset/" && \
		 echo "  4. Run: make extract-dataset" && exit 1)
	@echo ""
	@echo "Extracting dataset files..."
	@unzip -o $(DATASET_ZIP) -d dataset/
	@echo ""
	@echo "Cleaning up zip file..."
	@rm -f $(DATASET_ZIP)
	@echo ""
	@echo "✓ Dataset downloaded and extracted successfully!"
	@echo ""
	@echo "Downloaded files:"
	@ls -lh dataset/*.csv 2>/dev/null || echo "No CSV files found"

# Extract dataset if zip file exists
extract-dataset:
	@if [ -f $(DATASET_ZIP) ]; then \
		echo "Extracting dataset files..."; \
		unzip -o $(DATASET_ZIP) -d dataset/; \
		echo ""; \
		echo "✓ Dataset extracted successfully!"; \
		echo ""; \
		echo "Downloaded files:"; \
		ls -lh dataset/*.csv 2>/dev/null || echo "No CSV files found"; \
	else \
		echo "ERROR: $(DATASET_ZIP) not found."; \
		echo "Please download the dataset manually from:"; \
		echo "https://www.kaggle.com/datasets/computingvictor/transactions-fraud-datasets"; \
		exit 1; \
	fi

# Clean downloaded dataset files
clean-dataset:
	@echo "Removing dataset files..."
	@rm -f dataset/*.csv dataset/*.zip
	@echo "✓ Dataset files removed"

# Full setup: download and extract
setup-dataset: download-dataset
	@echo ""
	@echo "========================================="
	@echo "Dataset setup complete!"
	@echo "========================================="
	@echo ""
	@echo "You can now:"
	@echo "  1. Start the application: ./mvnw spring-boot:run"
	@echo "  2. Import data via REST API:"
	@echo "     curl -X POST http://localhost:8080/api/dataset/users/import"
	@echo "     curl -X POST http://localhost:8080/api/dataset/cards/import"
	@echo "     curl -X POST http://localhost:8080/api/dataset/transactions/import"
