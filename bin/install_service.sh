#!/bin/bash
# Usage: sudo ./install_service.sh <username>
set -e

if [ "$EUID" -ne 0 ]; then
  echo "Please run as root (sudo)"
  exit 1
fi

if [ -z "$1" ]; then
  echo "Usage: sudo ./install_service.sh <username>"
  exit 1
fi

USER=$1
SERVICE_FILE="dht22-server.service"
SERVICE_PATH="/etc/systemd/system/dht22-server@${USER}.service"
PROJECT_DIR="/home/${USER}/dht22-server"

cp "$SERVICE_FILE" "$SERVICE_PATH"
chown root:root "$SERVICE_PATH"
chmod 644 "$SERVICE_PATH"

systemctl daemon-reload
systemctl enable dht22-server@${USER}
systemctl start dht22-server@${USER}

echo "Service installed and started as dht22-server@${USER}"