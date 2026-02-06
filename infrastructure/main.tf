terraform {
  required_version = ">= 1.0.0"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 6.31"
    }
  }

  # Backend configuration for remote state
  # Uncomment for production use
  # backend "s3" {
  #   bucket         = "devsecops-demo-tfstate"
  #   key            = "terraform.tfstate"
  #   region         = "us-east-1"
  #   encrypt        = true
  #   dynamodb_table = "terraform-locks"
  # }
}

provider "aws" {
  region = var.aws_region

  default_tags {
    tags = {
      Project     = "DevSecOps-Demo"
      Environment = var.environment
      ManagedBy   = "Terraform"
      Repository  = "github-devsecops-demo"
    }
  }
}
